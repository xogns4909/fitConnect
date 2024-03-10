import React, { useEffect, useState } from 'react';
import axiosInstance from '../global/axiosConfig';
import { Button, Card, ListGroup, ListGroupItem, Pagination,Image  } from 'react-bootstrap';
import RegistrationButton from '../components/Registration';
import CreateChatRoom from "../components/CreateChatRoom";
import { translateCity, translateCategory } from './Translations';

const EventDetail = ({ eventId }) => {
  const [event, setEvent] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchEventDetail = async () => {
      try {
        const response = await axiosInstance.get(`/api/events/${eventId}/detail`);
        setEvent(response.data);
        console.log(response);
      } catch (err) {
        console.log(err.response);
        setError(err.response?.data?.message || '이벤트 상세 정보를 가져오는데 실패했습니다.');
      }


    };

    fetchEventDetail();
  }, [eventId]);

  const getImageUrl = (filePath) => {
    const baseUrl = "http://localhost:8080";
    return `${baseUrl}/${filePath.replace(/\\/g, '/')}`;
  };

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const response = await axiosInstance.get(`/api/reviews/events/${eventId}`, {
          params: {
            page: currentPage ,
            size: 10,
          },
        });
        setReviews(response.data.content);
        setTotalPages(response.data.totalPages);
      } catch (err) {
        console.log(err.response);
        // 리뷰 로딩 실패 처리
      }
    };

    fetchReviews();
  }, [eventId, currentPage]);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber); // 페이지 번호 변경 처리
  };

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
            {event.url && event.url.length > 0 && (
                <div>
                  {event.url.map((imageUrl, index) => (
                      <Image key={index} src={getImageUrl(imageUrl)} alt={`Event Image ${index}`} thumbnail fluid className="my-3" />
                  ))}
                </div>
            )}
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
            {reviews.length > 0 ? (
                reviews.map((review) => (
                    <ListGroupItem key={review.id}>
                      <strong>평점: {review.rating}</strong>
                      <p>{review.reviewContent}<  /p>
                      <div className="review-footer">
                        <small>작성자: {review.nickName}</small>
                      </div>
                    </ListGroupItem>
                ))
            ) : (
                <ListGroupItem>리뷰가 없습니다.</ListGroupItem>
            )}
          </ListGroup>
          {totalPages > 1 && (
              <Pagination className="justify-content-center">
                {[...Array(totalPages).keys()].map(number => (
                    <Pagination.Item key={number + 1} active={number + 1 === currentPage} onClick={() => handlePageChange(number + 1)}>
                      {number + 1}
                    </Pagination.Item>
                ))}
              </Pagination>
          )}
        </Card>
      </>
  );
};

export default EventDetail;
