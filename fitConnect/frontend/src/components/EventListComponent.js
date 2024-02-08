import React from 'react';
import { Card, Row, Col } from 'react-bootstrap';


const EventListComponent = ({ events }) => {

  const handleClick = (eventId) => {
    window.location.href = `/events/${eventId}`;
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString('ko-KR', {
      year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit'
    });
  };

  return (
      <>
        {events.map(event => (
            <Card key={event.id} className="mb-3" onClick={() => handleClick(event.id)}> {}
              <Card.Body>
                <Row>
                  <Col md={3}>
                    <strong>제목:</strong> {event.eventDetail.title}
                  </Col>
                  <Col md={3}>
                    <strong>운동 종목:</strong> {event.category}
                  </Col>
                  <Col md={6}>
                    <strong>운동 시간:</strong> {formatDate(event.eventDetail.startDate)} ~ {formatDate(event.eventDetail.endDate)}
                  </Col>
                </Row>
              </Card.Body>
            </Card>
        ))}
      </>
  );
};


export default EventListComponent;
