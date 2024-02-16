import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Button, Card, ListGroup,ListGroupItem } from 'react-bootstrap';
import RegistrationButton from '../components/Registration';
import CreateChatRoom from "../components/CreateChatRoom";
import { translateCity, translateCategory } from './Translations';
const EventDetail = ({ eventId }) => {
  const [event, setEvent] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchEventDetail = async () => {
      try {
        const response = await axios.get(`/api/events/${eventId}/detail`);
        setEvent(response.data);
        console.log(response.data)
      } catch (err) {
        setError(err.response?.data?.message || '이벤트 상세 정보를 가져오는데 실패했습니다.');
      }
    };

    fetchEventDetail();
  }, [eventId]);

  if (error) return <div>오류: {error}</div>;
  if (!event) return <div>로딩 중...</div>;

  return (
      <>
      <Card className="my-4 shadow">
        <Card.Body>
          <Card.Title className="display-4">{event.eventDetail.title}</Card.Title>
          <ListGroup className="list-group-flush">
            <ListGroup.Item>종목: {translateCategory(event.category)}</ListGroup.Item>
            <ListGroup.Item>운동 시간: {new Date(event.eventDetail.startDate).toLocaleString()} ~ {new Date(event.eventDetail.endDate).toLocaleString()}</ListGroup.Item>
            <ListGroup.Item>신청 기간: {new Date(event.registrationPolicy.registrationStart).toLocaleString()} ~ {new Date(event.registrationPolicy.registrationEnd).toLocaleString()}</ListGroup.Item>
            <ListGroup.Item>최대 참가자: {event.registrationPolicy.maxParticipants}</ListGroup.Item>
            <ListGroup.Item>위치: {translateCity(event.location.city)}, {event.location.address}</ListGroup.Item>
          </ListGroup>
          <Card.Text className="mt-4">{event.eventDetail.description}</Card.Text>
          <div className="mt-4">
            <RegistrationButton eventId={eventId} />
            <CreateChatRoom eventId={eventId} />
          </div>
        </Card.Body>
      </Card>
        <Card className="mb-4 shadow">
          <Card.Header>리뷰</Card.Header>
          <ListGroup variant="flush">
            {event.reviews.length > 0 ? (
                event.reviews.map((review) => (
                    <ListGroupItem key={review.id}>
                      <strong>평점: {review.rating}</strong>
                      <p>{review.content}</p>
                      <small>작성일: {new Date(review.createdAt).toLocaleString()}</small>
                    </ListGroupItem>
                ))
            ) : (
                <ListGroupItem>리뷰가 없습니다.</ListGroupItem>
            )}
          </ListGroup>
        </Card>
      </>
  );
};

export default EventDetail;
