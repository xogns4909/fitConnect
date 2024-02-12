import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { ListGroup, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import ReviewForm from './ReviewForm'; // ReviewForm 컴포넌트를 임포트합니다.

const RegisteredEventsList = () => {
  const [events, setEvents] = useState([]);
  const [showReviewForm, setShowReviewForm] = useState(false);
  const [selectedEventId, setSelectedEventId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetchRegisteredEvents();
  }, []);

  const fetchRegisteredEvents = async () => {
    try {
      const response = await axios.get(`/mypage/registrations`);
      setEvents(response.data.content || []);
    } catch (error) {
      console.error('Error fetching registered events:', error);
    }
  };

  const handleCancelRegistration = async (registrationId) => {
    try {
      await axios.delete(`/api/registrations/${registrationId}`);
      fetchRegisteredEvents(); // 등록 취소 후 목록 새로고침
    } catch (error) {
      console.error('Error cancelling registration:', error);
    }
  };

  const openReviewForm = (eventId) => {
    setSelectedEventId(eventId);
    setShowReviewForm(true);
  };

  const closeReviewForm = () => {
    setShowReviewForm(false);
  };

  const handleReviewSubmitted = () => {
    fetchRegisteredEvents();
  };

  return (
      <>
        <ListGroup>
          {events.map((event) => (
              <ListGroup.Item key={event.id}>
                <h5>{event.title}</h5>
                <p>신청 상태: {event.status}</p>
                {['APPLIED', 'APPROVED'].includes(event.status) && (
                    <Button variant="danger" onClick={() => handleCancelRegistration(event.registrationId)}>
                      신청 취소
                    </Button>
                )}
                <Button variant="primary" onClick={() => navigate(`/events/${event.eventId}`)}>상세 보기</Button>
                <Button variant="secondary" onClick={() => openReviewForm(event.eventId)}>리뷰 작성</Button>
              </ListGroup.Item>
          ))}
        </ListGroup>
        <ReviewForm
            show={showReviewForm}
            handleClose={closeReviewForm}
            eventId={selectedEventId}
            onReviewSubmitted={handleReviewSubmitted}
        />
      </>
  );
};

export default RegisteredEventsList;
