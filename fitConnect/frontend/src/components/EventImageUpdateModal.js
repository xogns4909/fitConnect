import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { useParams, useNavigate } from 'react-router-dom'
import axiosInstance from '../global/axiosConfig';

const ImageUploadModal = ({ show, handleClose }) => {
  const { eventId } = useParams();
  const [selectedImages, setSelectedImages] = useState([]);
  const navigate = useNavigate();

  const handleImageChange = (e) => {
    // 파일 객체 목록을 배열로 변환하고 상태에 설정
    setSelectedImages(Array.from(e.target.files));
  };

  const handleImageUpload = async () => {
    const formData = new FormData();
    selectedImages.forEach((image) => {
      formData.append('images', image);
    });

    try {
      await axiosInstance.patch(`/api/events/${eventId}/image`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      alert('Images updated successfully');
      handleClose();
      navigate(`/events/${eventId}`);
    } catch (error) {
      console.error('Error updating image:', error);
      alert('Failed to update the images');
    }
  };

  return (
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Update Event Images</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group controlId="formEventImage">
            <Form.Label>Event Images</Form.Label>
            <Form.Control
                type="file"
                multiple // 여러 파일 선택 활성화
                onChange={handleImageChange}
            />
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={handleImageUpload}>
            Upload Images
          </Button>
        </Modal.Footer>
      </Modal>
  );
};

export default ImageUploadModal;
