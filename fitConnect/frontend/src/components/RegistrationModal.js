import React, { useState, useEffect } from 'react';
import { Modal, Button, ListGroup, Pagination } from 'react-bootstrap';
import axios from 'axios';

const RegistrationModal = ({ eventId, show, onHide }) => {
    const [registrations, setRegistrations] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [pageSize, setPageSize] = useState(10);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        if (show) {
            fetchRegistrations();
        }
    }, [show, eventId, currentPage]);

    const fetchRegistrations = async () => {
        try {
            const params = { page: currentPage, size: pageSize };
            const response = await axios.get(`/api/registrations/${eventId}`, { params });
            setRegistrations(response.data.content || []);
            setTotalPages(response.data.totalPages || 0);
        } catch (error) {
            const errorCode = error.response?.data
            let message = "승인에 실패하였습니다."
            if(errorCode){
                message = errorCode;
            }
            alert(message);
        }
    };

    const handleApproval = async (registrationId, approved) => {
        try {
            const endpoint = approved
                ? `/api/registrations/${registrationId}/approve?eventId=${eventId}`
                : `/api/registrations/${registrationId}/deny`;
            await axios.post(endpoint);
            fetchRegistrations();
        } catch (error) {
            console.error('Error updating registration:', error);
        }
    };

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const paginationItems = [];
    for (let number = 1; number <= totalPages; number++) {
        paginationItems.push(
            <Pagination.Item key={number} active={number === currentPage + 1} onClick={() => handlePageChange(number - 1)}>
                {number}
            </Pagination.Item>,
        );
    }

    return (
        <Modal show={show} onHide={onHide}>
            <Modal.Header closeButton>
                <Modal.Title>신청 내역</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <ListGroup>
                    {registrations.map(reg => (
                        <ListGroup.Item key={reg.id}>
                            상태: {reg.status}
                            {reg.status === 'APPLIED' && (
                                <>
                                    <Button size="sm" variant="success" onClick={() => handleApproval(reg.registrationId, true)}>승인</Button>
                                    <Button size="sm" variant="danger" onClick={() => handleApproval(reg.registrationId, false)}>거부</Button>
                                </>
                            )}
                        </ListGroup.Item>
                    ))}
                </ListGroup>
                <Pagination className="justify-content-center">{paginationItems}</Pagination>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>닫기</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default RegistrationModal;
