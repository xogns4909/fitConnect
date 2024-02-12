import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { ListGroup,Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
const ReviewsList = () => {
  const [reviews, setReviews] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    fetchReviews();
  }, []);

  const fetchReviews = async () => {
    try {
      const response = await axios.get(`/mypage/reviews`);
      setReviews(response.data.content);
      console.log(response.data.content);
    } catch (error) {
      console.error('Error fetching reviews:', error);
    }
  };

  const deleteReview = async (reviewId) => {
    try {
      await axios.delete(`/api/reviews/${reviewId}`);
      fetchReviews();
    } catch (error) {
      console.error('Error deleting review:', error);
    }
  };

  return (
      <ListGroup>
        {reviews.map(review => (
            <ListGroup.Item key={review.id}>
              <h5>{review.eventTitle}</h5>
              <p>내용: {review.reviewContent}</p>
              <p>평점: {review.rating}</p>
              <Button variant="primary" onClick={() => navigate(`/events/${review.eventId}`)}>상세 보기</Button>{' '}
              <Button variant="danger" onClick={() => deleteReview(review.reviewId)}>삭제</Button>
            </ListGroup.Item>
        ))}
      </ListGroup>
  );
};

export default ReviewsList;
