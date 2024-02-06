import React, { useState, useEffect } from 'react';
import { Modal, Button, Form, ListGroup } from 'react-bootstrap';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const ChatModal = ({ show, onHide, chatRoomId }) => {
  const [message, setMessage] = useState('');
  const [messages, setMessages] = useState([]);
  const [stompClient, setStompClient] = useState(null);

  useEffect(() => {
    if (show) {
      const socket = new SockJS('http://localhost:8080/ws');
      const client = Stomp.over(socket);
      client.connect({}, () => {
        console.log("Connected: " + chatRoomId);
        console.log(stompClient)
        stompClient.subscribe(`/topic/chatrooms/${chatRoomId}`, (message) => {
          const newMessage = JSON.parse(message.body);
          setMessages((prevMessages) => [...prevMessages, newMessage]);
        });
      });
      setStompClient(client);

      return () => {
        if (stompClient !== null) {
          stompClient.disconnect();
          console.log("Disconnected");
        }
      };
    }
  }, [show, chatRoomId]);

  const handleSendMessage = () => {
    if (message && stompClient) {
      stompClient.send(`/app/chat/${chatRoomId}/sendMessage`, {}, JSON.stringify(message));
      setMessage('');
    }else{
      console.log(message , stompClient)
    }
  };

  return (
      <Modal show={show} onHide={onHide}>
        <Modal.Header closeButton>
          <Modal.Title>채팅</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <ListGroup>
            {messages.map((msg, index) => (
                <ListGroup.Item key={index}>{msg.content}</ListGroup.Item>
            ))}
          </ListGroup>
          <Form>
            <Form.Group className="mb-3">
              <Form.Control
                  type="text"
                  placeholder="메시지 입력..."
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
  );
};

export default ChatModal;
