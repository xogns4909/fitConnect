import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Card, ListGroup, Button, Pagination } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const MyEventsList = () => {
  const [myEvents, setMyEvents] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const navigate = useNavigate();

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

  return (
      <>
        <ListGroup>
          {myEvents.map(event => (
              <ListGroup.Item key={event.id} className="mb-3">
                <Card>
                  <Card.Body>
                    <Card.Title>{event.eventDetail.title}</Card.Title>
                    <Card.Subtitle className="mb-2 text-muted">{event.category}</Card.Subtitle>
                    <Card.Text>
                      <strong>Description:</strong> {event.eventDetail.description}
                    </Card.Text>
                    <Card.Text>
                      <strong>Start:</strong> {new Date(event.eventDetail.startDate).toLocaleString()}
                    </Card.Text>
                    <Card.Text>
                      <strong>End:</strong> {new Date(event.eventDetail.endDate).toLocaleString()}
                    </Card.Text>
                    <Card.Text>
                      <strong>Location:</strong> {event.location.city}, {event.location.address}
                    </Card.Text>
                    <Button variant="primary" onClick={() => navigate(`/events/${event.id}`)}>상세 보기</Button>
                    <Button variant="secondary" className="mx-2" onClick={() => navigate(`/events/edit/${event.id}`)}>수정</Button>
                    <Button variant="danger" onClick={() => handleDelete(event.id)}>삭제</Button>
                  </Card.Body>
                </Card>
              </ListGroup.Item>
          ))}
        </ListGroup>
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