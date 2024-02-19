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
        console.log("response = ",response.data)
        setEvent(response.data);

      } catch (err) {
        console.log(err.response);
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
          <Card.Title className="display-4">{event.title}</Card.Title>
          <ListGroup className="list-group-flush">
            <ListGroup.Item>운동 시간: {new Date(event.startTime).toLocaleString()} ~ {new Date(event.endTime).toLocaleString()}</ListGroup.Item>
            <ListGroup.Item>신청 기간: {new Date(event.registrationStart).toLocaleString()} ~ {new Date(event.registrationEnd).toLocaleString()}</ListGroup.Item>
            <ListGroup.Item>최대 참가자: {event.maxParticipants}</ListGroup.Item>
            <ListGroup.Item>종목: {translateCategory(event.category)}</ListGroup.Item>
            <ListGroup.Item>위치: {translateCity(event.city)}, {event.address}</ListGroup.Item>
          </ListGroup>
          <Card.Text className="mt-4">{event.description}</Card.Text>
          <div className="mt-4">
            <RegistrationButton eventId={eventId} />
            <CreateChatRoom eventId={eventId} />
          </div>
        </Card.Body>
      </Card>
        <Card className="mb-4 shadow">
          <Card.Header>리뷰</Card.Header>
          <ListGroup variant="flush">
            {event.reviewResponseDtoList.length > 0 ? (
                event.reviewResponseDtoList.map((review) => (
                    <ListGroupItem key={review.id}>
                      <strong>평점: {review.rating}</strong>
                      <p>{review.reviewContent}</p>

                      <div className="review-footer">
                        <small>작성자: {review.nickName}  </small>
                      </div>
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
