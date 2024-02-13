import React, { useState, useEffect } from 'react';
import { Modal, Button, ListGroup } from 'react-bootstrap';
import axios from 'axios';

const RegistrationModal = ({ eventId, show, onHide }) => {
    const [registrations, setRegistrations] = useState([]);

    useEffect(() => {
        if (show) {
            fetchRegistrations();
        }
    }, [show, eventId]);

    const fetchRegistrations = async () => {
        try {
            const params = {
                page: 0,
                size: 10
            };
            const response = await axios.get(`/api/registrations/${eventId}`, { params });
            console.log(response.data.content)
            setRegistrations(response.data.content || []);
        } catch (error) {
            console.error('Error fetching registrations:', error);
        }
    };

    const handleApproval = async (registrationId, approved) => {
        try {
            const endpoint = `/api/registrations/${registrationId}/${approved ? 'approve' : 'deny'}`;
            await axios.post(endpoint);
            fetchRegistrations();
        } catch (error) {
            console.error('Error updating registration:', error);
        }
    };

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
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>닫기</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default RegistrationModal;
