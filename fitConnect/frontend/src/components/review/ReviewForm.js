import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import axiosInstance from '../../global/axiosConfig';
import { useNavigate } from 'react-router-dom';


const ReviewForm = ({ show, handleClose, eventId, onReviewSubmitted }) => {
  const [content, setContent] = useState('');
  const [rating, setRating] = useState(5);
  const navigate = useNavigate();

  const submitReview = async () => {
    try {
      const reviewRegistrationDto = {
        content,
        rating,
        exerciseEventId: eventId,
      };
      await axiosInstance.post(`/api/reviews`, reviewRegistrationDto);
      onReviewSubmitted();
      alert("요청이 성공적으로 처리되었습니다.");
      handleClose();
      navigate(`/events/${eventId}`);
    } catch (error) {
      console.error('Error submitting review:', error);
      const errorMessage = error.response?.data || "승인/거부 처리 중 오류가 발생했습니다.";
      alert(errorMessage);
    }
  };

  return (
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>리뷰 작성</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Rating</Form.Label>
              <Form.Control
                  type="number"
                  placeholder="Enter rating"
                  value={rating}onChange={(e) => setRating(Math.max(0, Math.min(5, Number(e.target.value))))}
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Content</Form.Label>
              <Form.Control
                  as="textarea"
                  rows={3}
                  placeholder="Review content"
                  value={content}

                  onChange={(e) => setContent(e.target.value)}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            닫기
          </Button>
          <Button variant="primary" onClick={submitReview}>
            제출
          </Button>
        </Modal.Footer>
      </Modal>
  );
};

export default ReviewForm;
