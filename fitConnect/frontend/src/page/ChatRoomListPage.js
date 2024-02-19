import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import {
  Card,
  ListGroup,
  Container,
  Row,
  Col,
  Button,
  Pagination
} from 'react-bootstrap';
import Navbar from "../components/Navbar";
import ChatModal from "../components/ChatModal";

const ChatRoomPage = () => {
  const [chatRooms, setChatRooms] = useState([]); // 채팅방 목록을 저장할 상태
  const [currentPage, setCurrentPage] = useState(0); // 현재 페이지
  const [totalPages, setTotalPages] = useState(0); // 전체 페이지 수
  const [error, setError] = useState('');
  const [modalShow, setModalShow] = useState(null); // 현재 열려있는 모달의 ID

  useEffect(() => {
    fetchChatRooms(currentPage);
  }, [currentPage]);

  const fetchChatRooms = async (page) => {
    try {
      const params = {
        page,
        size: 10,
      };
      const response = await axios.get(`/api/chatrooms/messages`, { params }); // API 엔드포인트 확인 필요
      setChatRooms(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      setError('채팅방을 불러오는데 실패했습니다.');
      console.error(error);
    }
  };

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };


  const handleDeleteChatRoom = async (chatRoomId) => {
    try {
      await axios.delete(`/api/chatrooms/${chatRoomId}`);
      alert("채팅방이 성공적으로 삭제되었습니다.");
      fetchChatRooms(currentPage); // 채팅방 목록 다시 불러오기
    } catch (error) {
      console.error('채팅방 삭제 중 오류가 발생했습니다:', error);
      alert("채팅방 삭제에 실패했습니다.");
    }
  };

  return (
      <>
        <Navbar />
        <Container className="mt-4">
          <Row className="justify-content-center">
            <Col lg={8}>
              {error && <p className="text-danger">{error}</p>}
              <Card className="shadow-sm">
                <Card.Header as="h5">채팅방 목록</Card.Header>
                <ListGroup variant="flush">
                  {chatRooms.map((chatRoom) => (
                      <ListGroup.Item key={chatRoom.id}>
                        <div className="d-flex justify-content-between align-items-center">
                          <div>
                            <h6>{chatRoom.title}</h6>
                            <small>
                              마지막 업데이트: {new Date(chatRoom.updatedAt).toLocaleString()}
                            </small>
                            <p>{chatRoom.messages.length > 0 ? chatRoom.messages[chatRoom.messages.length - 1].content : "메시지 없음"}</p>
                          </div>
                          <div className="d-flex justify-content-between align-items-center">
                            {/* 기존 코드 */}
                            <Button variant="primary" onClick={() => setModalShow(chatRoom.id)}>
                              채팅하기
                            </Button>
                            <Button variant="danger" onClick={() => handleDeleteChatRoom(chatRoom.id)}>
                              삭제하기
                            </Button>
                          </div>
                        </div>
                        {modalShow === chatRoom.id && (
                            <ChatModal
                                show={modalShow === chatRoom.id}
                                onHide={() => setModalShow(null)}
                                chatRoomId={chatRoom.id}
                            />
                        )}
                      </ListGroup.Item>
                  ))}
                </ListGroup>
              </Card>
              <Pagination className="justify-content-center">
                {[...Array(totalPages).keys()].map((page) => (
                    <Pagination.Item key={page} active={page === currentPage} onClick={() => handlePageChange(page)}>
                      {page + 1}
                    </Pagination.Item>
                ))}
              </Pagination>
            </Col>
          </Row>
        </Container>
      </>
  );
};

export default ChatRoomPage;
