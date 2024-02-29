import React, {useState, useEffect} from 'react';
import axiosInstance from '../global/axiosConfig';
import {ListGroup, Button, Pagination} from 'react-bootstrap';
import {useNavigate} from 'react-router-dom';
import ReviewForm from './ReviewForm';

const RegisteredEventsList = () => {
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [events, setEvents] = useState([]);
  const [showReviewForm, setShowReviewForm] = useState(false);
  const [selectedEventId, setSelectedEventId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetchRegisteredEvents();
  }, [currentPage]);

  const fetchRegisteredEvents = async () => {
    try {
      const params = {page: currentPage, size: pageSize};
      const response = await axiosInstance.get(`/mypage/registrations`, {params});
      setEvents(response.data.content || []);
      setTotalPages(response.data.totalPages || 0);
      console.log(events)
    } catch (error) {
      console.error('Error fetching registered events:', error);
    }
  };

  const handleCancelRegistration = async (registrationId) => {
    try {
      await axiosInstance.delete(`/api/registrations/${registrationId}`);
      fetchRegisteredEvents();
    } catch (error) {
      console.error('Error cancelling registration:', error);
    }
  };

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const openReviewForm = (eventId) => {
    setSelectedEventId(eventId);
    setShowReviewForm(true);
  };

  const closeReviewForm = () => {
    setShowReviewForm(false);
  };

  const handleReviewSubmitted = () => {
    fetchRegisteredEvents();
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
          {events.length > 0 ? (
              events.map((event) => (
                  <ListGroup.Item key={event.id}>
                    <h5>{event.title}</h5>
                    <p>신청 상태: {event.status}</p>
                    {['APPLIED', 'APPROVED'].includes(event.status) && (
                        <Button variant="danger"
                                onClick={() => handleCancelRegistration(
                                    event.registrationId)}>
                          신청 취소
                        </Button>
                    )}
                    <Button variant="primary" onClick={() => navigate(
                        `/events/${event.eventId}`)}>상세 보기</Button>
                    <Button variant="secondary"
                            onClick={() => openReviewForm(event.eventId)}>리뷰
                      작성</Button>
                  </ListGroup.Item>
              ))
          ) : (
              <ListGroup.Item>등록된 이벤트가 없습니다.</ListGroup.Item>
          )}
        </ListGroup>
        <Pagination
            className="justify-content-center">{paginationItems}</Pagination> {}
        <ReviewForm
            show={showReviewForm}
            handleClose={closeReviewForm}
            eventId={selectedEventId}
            onReviewSubmitted={handleReviewSubmitted}
        />

      </>
  );
};

export default RegisteredEventsList;
