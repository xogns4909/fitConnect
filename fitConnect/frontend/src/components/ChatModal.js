import React, { useState, useEffect } from 'react';
import { Modal, Button, Form, ListGroup } from 'react-bootstrap';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import axios from 'axios';

const ChatModal = ({ show, onHide, chatRoomId }) => {
  const [message, setMessage] = useState('');
  const [messages, setMessages] = useState([]);
  const [client, setClient] = useState(null);
  const [currentUserId, setCurrentUserId] = useState(null);

  useEffect(() => {
    const fetchCurrentUserInfo = async () => {
      try {
        const { data } = await axios.get('/user');
        setCurrentUserId(data.id);
      } catch (error) {
        console.error('Failed to fetch current user info:', error);
      }
    };

    if (show) {
      fetchCurrentUserInfo();
      const socket = new SockJS('http://localhost:8080/ws');
      const stompClient = Stomp.over(socket);
      stompClient.connect({}, () => {
        setClient(stompClient);
        stompClient.subscribe(`/topic/${chatRoomId}`, (msg) => {
          const newMessage = JSON.parse(msg.body);
          setMessages(prevMessages => [...prevMessages, newMessage.body]);
        });
      });

      fetchMessages();

      return () => {
        if (client) {
          client.disconnect();
        }
      };
    }
  }, [show, chatRoomId]);

  const fetchMessages = async () => {
    try {
      const response = await axios.get(`/api/chatmessages?chatRoomId=${chatRoomId}`);
      setMessages(response.data.reverse());
    } catch (error) {
      console.error('Failed to fetch messages:', error);
    }
  };

  const handleSendMessage = () => {
    if (message && client) {
      client.send(`/app/chat/${chatRoomId}/sendMessage`, {}, JSON.stringify({ content: message }));
      setMessage('');
    }
  };

  return (
      <>
        <style>{`
        .message-container {
          display: flex;
          flex-direction: column;
        }
        .message-bubble {
          display: flex;
          flex-direction: column;
          max-width: 80%;
          padding: 8px 12px;
          border-radius: 20px;
          margin: 5px 0;
          background-color: #f0f0f0;
        }
        .my-message {
          margin-left: auto;
          background-color: #dcf8c6;
        }
        .other-message {
          margin-right: auto;
        }
        .message-metadata {
          font-size: 12px;
          color: #666;
          text-align: right;
        }
      `}</style>
        <Modal show={show} onHide={onHide}>
          <Modal.Header closeButton>
            <Modal.Title>채팅</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div className="message-container">
              {messages.map((msg, index) => (
                  <div
                      key={index}
                      className={`message-bubble ${currentUserId === msg.userId ? 'my-message' : 'other-message'}`}
                  >
                    <div>{msg.content}</div>
                    <div className="message-metadata">
                      , {new Date(msg.sentAt).toLocaleTimeString()}
                    </div>
                  </div>
              ))}
            </div>
            <Form>
              <Form.Group className="mb-3">
                <Form.Control
                    type="text"
                    placeholder="메시지를 입력하세요..."
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                />
              </Form.Group>
              <Button variant="primary" onClick={handleSendMessage}>
                전송
              </Button>
            </Form>
          </Modal.Body>
        </Modal>
      </>
  );
};

export default ChatModal;
