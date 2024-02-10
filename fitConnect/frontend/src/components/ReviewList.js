import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { ListGroup } from 'react-bootstrap';

const ReviewsList = () => {
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    fetchReviews();
  }, []);

  const fetchReviews = async () => {
    try {
      const response = await axios.get(`/mypage/reviews`);
      setReviews(response.data.content);
    } catch (error) {
      console.error('Error fetching reviews:', error);
    }
  };

  return (
      <ListGroup>
        {reviews.map(review => (
            <ListGroup.Item key={review.id}>
              <h5>{review.title}</h5>
              <p>{review.content}</p>
            </ListGroup.Item>
        ))}
      </ListGroup>
  );
};

export default ReviewsList;
