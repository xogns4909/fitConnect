import React from 'react';
import { Card, Row, Col, Button } from 'react-bootstrap';
import { translateCity, translateCategory } from './Translations';
import { useNavigate } from 'react-router-dom';



const EventListComponent = ({ events }) => {
  const navigate = useNavigate();

  const handleClick = (eventId) => {
    navigate(`/events/${eventId}`);
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  return (
      <>
        {events.map(event => (
            <Card key={event.id} className="mb-4 shadow" style={{ cursor: 'pointer' }}>
              <Card.Body>
                <Row>
                  <Col md={8}>
                    <Card.Title className="mb-2">{event.title}</Card.Title>
                    <Card.Text>
                      <strong>종목:</strong> {translateCategory(event.category)}
                    </Card.Text>
                    <Card.Text>
                      <strong>시간:</strong> {formatDate(event.startTime)} ~ {formatDate(event.endTime)}
                    </Card.Text>
                    <Card.Text>
                      <div><small>작성자: {event.organizerNickname}</small></div>
                      <div><small>작성 시간: {formatDate(event.writeTime)}</small></div>
                    </Card.Text>

                  </Col>
                  <Col md={4} className="d-flex align-items-center justify-content-end">
                    <Button variant="primary" onClick={() => handleClick(event.id)}>상세 보기</Button>
                  </Col>
                </Row>
              </Card.Body>
            </Card>
        ))}
      </>
  );
};

export default EventListComponent;
