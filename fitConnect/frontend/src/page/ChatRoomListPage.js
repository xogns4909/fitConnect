import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Card, ListGroup, Container, Row, Col, Badge } from 'react-bootstrap';
import Navbar from "../components/Navbar";

const ChatRoomPage = () => {
  const { chatRoomId } = useParams();
  const [chatRoom, setChatRoom] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchMessages = async () => {
      try {
        const params = {
          page: 0,
          size: 10,
        };
        const response = await axios.get(`/api/chatrooms/messages`, { params });
        setChatRoom(response.data);
        console.log(response.data);
      } catch (error) {
        setError('메시지를 불러오는데 실패했습니다.');
      }
    };

    fetchMessages();
  }, [chatRoomId]);

  return (
      <>
        <Navbar/>
        <Container className="mt-4">
          <Row className="justify-content-center">
            <Col lg={8}>
              {error && <p className="text-danger">{error}</p>}
              <Card className="shadow-sm">
                <Card.Header as="h5">
                  채팅방 메시지 <Badge bg="secondary">New</Badge>
                </Card.Header>
                <ListGroup variant="flush">
                  {chatRoom && chatRoom.content.length > 0 ? (
                      chatRoom.content.map((message) => (
                          <ListGroup.Item key={message.id}>
                            <h6>{message.title}</h6>
                            <p className="mb-1">{message.content}</p>
                            <small>업데이트: {new Date(message.updatedAt).toLocaleString()}</small>
                          </ListGroup.Item>
                      ))
                  ) : (
                      <ListGroup.Item>채팅 메시지가 없습니다.</ListGroup.Item>
                  )}
                </ListGroup>
              </Card>
            </Col>
          </Row>
        </Container>
      </>
  );
};

export default ChatRoomPage;
