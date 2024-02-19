import React from 'react';
import {Card, Row, Col} from 'react-bootstrap';
import {translateCity, translateCategory} from './Translations';

const EventListComponent = ({events}) => {

  const handleClick = (eventId) => {
    window.location.href = `/events/${eventId}`;
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
            <Card key={event.id} className="mb-3"
                  onClick={() => handleClick(event.id)}>
              <Card.Body>
                <div><
                  strong>제목:</strong> {event.title} |
                  <strong>운동 종목:</strong> {translateCategory(event.category)} |
                  <strong>운동 시간:</strong> {formatDate(event.startTime)} ~ {formatDate(event.endTime)} |
                  <small>   작성자: {event.organizerNickname}</small></div>
              </Card.Body>
            </Card>
        ))}
      </>
  );
};

export default EventListComponent;
