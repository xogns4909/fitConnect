import React, { useState, useEffect, useRef } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import axiosInstance from '../../global/axiosConfig';
import MessageUpdateMenu from "./MessageUpdateMenu";

const ChatModal = ({ show, onHide, chatRoomId }) => {
  const [message, setMessage] = useState('');
  const [messages, setMessages] = useState([]);
  const [client, setClient] = useState(null);
  const [currentUserId, setCurrentUserId] = useState(null);
  const [activeMessage, setActiveMessage] = useState(null);

  useEffect(() => {

    const fetchCurrentUserInfo = async () => {
      try {
        const { data } = await axiosInstance.get('/user');
        setCurrentUserId(data.id);
        console.log(data.id);
      } catch (error) {
        console.error('Failed to fetch current user info:', error);
      }
    };

    if (show) {
      fetchCurrentUserInfo();
      const socket = new SockJS('/ws');
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
      const response = await axiosInstance.get(`/api/chatmessages?chatRoomId=${chatRoomId}`);
      setMessages(response.data.reverse());
    } catch (error) {
      console.error('Failed to fetch messages:', error);
    }
  };


  const handleSendMessage = async (e) => {
    if (e.key === 'Enter' || e.type === 'click') {
      e.preventDefault();
      if (message.trim() && client) {
        await client.send(`/app/chat/${chatRoomId}/sendMessage`, {}, JSON.stringify({ content: message.trim() }));
        setMessage('');

      }
    }
  };


  const handleDeleteMessage = async (messageId,messageTimestamp) => {
    const currentTime = new Date().getTime();
    const messageTime = new Date(messageTimestamp).getTime();
    const timeDiff = currentTime - messageTime;

    if (timeDiff > 5 * 60 * 1000) {
      alert("메시지는 생성 후 5분 이내에만 삭제할 수 있습니다.");
      return;
    }


    try {
      await axiosInstance.delete(`/api/chatmessages/${messageId}`);
      setMessages(currentMessages => currentMessages.filter(msg => msg.id !== messageId));
      fetchMessages()
    } catch (error) {
    }
  };


  return (
      <>
          <style>{`
          .sender-name {
          font-size: 14px;
          font-weight: bold;
          margin-bottom: 5px; 
          color: #555; 
          }

          
          .message-container {
            display: flex;
            flex-direction: column;
            max-height: 400px; 
            overflow-y: auto; 
            margin-bottom: 15px;
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

        <Modal size="lg" show={show} onHide={onHide}>
          <Modal.Header closeButton>
            <Modal.Title>채팅</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div className="message-container" style={{ maxHeight: '400px', overflowY: 'auto' }}>
              {messages.map((msg, index) => (
                  <MessageUpdateMenu
                      key={index}
                      message={msg}
                      messageId={msg.id}
                      onDelete={() => handleDeleteMessage(msg.id,msg.sentAt)}
                      activeMessage={activeMessage}
                      setActiveMessage={setActiveMessage}
                  >
                    <div className={`message-bubble ${currentUserId === msg.userId ? 'my-message' : 'other-message'}`}>
                      <div className="sender-name">{msg.senderName}</div>
                      <div>{msg.content}</div>
                      <div className="message-metadata">{new Date(msg.sentAt).toLocaleTimeString()}</div>
                    </div>
                  </MessageUpdateMenu>
              ))}
            </div>
            <Form>
              <Form.Group className="mb-3">
                <Form.Control
                    type="text"
                    placeholder="메시지를 입력하세요..."
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    onKeyPress={(e) => e.key === 'Enter' && handleSendMessage(e)}
                />
              </Form.Group>
              <Button variant="primary" onClick={handleSendMessage}>
                전송
              </Button>
            </Form>
            <div />
          </Modal.Body>
        </Modal>
      </>
  );
};

export default ChatModal;
