import React, {useEffect, useState} from 'react';
import axios from 'axios';
import RegistrationModal from './RegistrationModal';
import {Card, ListGroup, Button, Pagination} from 'react-bootstrap';
import {useNavigate} from 'react-router-dom';
import { translateCity, translateCategory } from './Translations';
const MyEventsList = () => {
  const [myEvents, setMyEvents] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
  const [currentEventId, setCurrentEventId] = useState(null);

  useEffect(() => {
    fetchMyEvents(currentPage);
  }, [currentPage]);

  const fetchMyEvents = async (page) => {
    try {
      const response = await axios.get(`/mypage/events?page=${page}&size=5`);
      setMyEvents(response.data.content || []);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error('Error fetching my events:', error);
    }
  };

  const handleDelete = async (eventId) => {
    try {
      await axios.delete(`/api/events/${eventId}`);
      alert("삭제 성공")
      fetchMyEvents();

    } catch (error) {
      console.error('Error deleting event:', error);
    }
  };

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleShowRegistrations = (eventId) => {
    setCurrentEventId(eventId);
    setShowModal(true);
  };

  return (
      <>
        <ListGroup>
          {myEvents.length > 0 ? (
              myEvents.map(event => (
                  <ListGroup.Item key={event.id} className="mb-3">
                    <Card>
                      <Card.Body>
                        <Card.Title>{event.eventDetail.title}</Card.Title>
                        <Card.Text>
                        <strong>종목: </strong>{translateCategory(event.category)}
                        </Card.Text>
                        <Card.Text>
                          <strong>설명:</strong> {event.eventDetail.description}
                        </Card.Text>
                        <Card.Text>
                          <strong>운동 시작시간:</strong> {new Date(
                            event.eventDetail.startDate).toLocaleString()}
                        </Card.Text>
                        <Card.Text>
                          <strong>운동 종료시간:</strong> {new Date(
                            event.eventDetail.endDate).toLocaleString()}
                        </Card.Text>
                        <Card.Text>
                          <strong>위치 : </strong>{translateCity(event.location.city)}, {event.location.address}
                        </Card.Text>
                        <Button variant="primary"
                                onClick={() => navigate(`/events/${event.id}`)}>상세
                          보기</Button>
                        <Button variant="secondary" className="mx-2"
                                onClick={() => navigate(
                                    `/events/edit/${event.id}`)}>수정</Button>
                        <Button variant="danger" onClick={() => handleDelete(
                            event.id)}>삭제</Button>
                        <Button variant="secondary"
                                onClick={() => handleShowRegistrations(
                                    event.id)}>신청 내역 보기</Button>
                      </Card.Body>
                    </Card>
                  </ListGroup.Item>

              ))) : (
              <ListGroup.Item>생성된 이벤트가 없습니다.</ListGroup.Item>
          )}
        </ListGroup>
        <RegistrationModal
            show={showModal}
            onHide={() => setShowModal(false)}
            eventId={currentEventId}
        />
        <Pagination className="justify-content-center mt-4">
          {[...Array(totalPages).keys()].map((number) => (
              <Pagination.Item
                  key={number}
                  active={number === currentPage}
                  onClick={() => handlePageChange(number)}
              >
                {number + 1}
              </Pagination.Item>
          ))}
        </Pagination>
      </>
  );
};

export default MyEventsList;
