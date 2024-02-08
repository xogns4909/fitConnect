import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { ListGroup, Button } from 'react-bootstrap';

const RegisteredEventsList = () => {
  const [events, setEvents] = useState([]);

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
      await axios.post(`/api/registrations/${registrationId}/cancel`);
      fetchRegisteredEvents(); // 신청 취소 후 목록을 다시 불러옵니다.
    } catch (error) {
      console.error('Error cancelling registration:', error);
    }
  };

  return (
      <ListGroup>
        {events.map((event) => (
            <ListGroup.Item key={event.id}>
              <h5>{event.title}</h5>
              <p>신청 상태: {event.status}</p>
              {['APPLIED', 'APPROVED'].includes(event.status) && (
                  <Button variant="danger" onClick={() => handleCancelRegistration(event.id)}>
                    신청 취소
                  </Button>
              )}
            </ListGroup.Item>
        ))}
      </ListGroup>
  );
};

export default RegisteredEventsList;
