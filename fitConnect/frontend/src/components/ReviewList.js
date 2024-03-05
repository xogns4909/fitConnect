import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {ListGroup, Button, Pagination} from 'react-bootstrap';
import {useNavigate} from 'react-router-dom';

const ReviewsList = () => {
  const [reviews, setReviews] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    fetchReviews();
  }, [currentPage]);

  const fetchReviews = async () => {
    try {
      const params = {page: currentPage, size: pageSize};
      const response = await axios.get(`/mypage/reviews`, {params});
      console.log("reuvew", response);
      setReviews(response.data.content);
      setTotalPages(response.data.totalPages || 0);
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

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  let paginationItems = [];
  for (let number = 1; number <= totalPages; number++) {
    paginationItems.push(
        <Pagination.Item key={number} active={number === currentPage + 1}
                         onClick={() => handlePageChange(number - 1)}>
          {number}
        </Pagination.Item>
    );
  }

  return (
      <>
        <ListGroup>
          {reviews.length > 0 ? (
              reviews.map(review => (
                  <ListGroup.Item key={review.id}>
                    <h5>{review.eventTitle}</h5>
                    <p>내용: {review.reviewContent}</p>
                    <p>평점: {review.rating}</p>
                    <div className="review-meta">
                      <small>작성자: {review.nickName}</small>
                      <Button variant="primary" onClick={() => navigate(`/events/${review.eventId}`)}>상세 보기</Button>{' '}
                      <Button variant="danger" onClick={() => deleteReview(review.reviewId)}>삭제</Button>
                    </div>
                  </ListGroup.Item>
              ))
          ) : (
              <ListGroup.Item>작성된 리뷰가 없습니다.</ListGroup.Item>
          )}
        </ListGroup>
        <Pagination
            className="justify-content-center">{paginationItems}</Pagination>
      </>
  );
};

export default ReviewsList;
