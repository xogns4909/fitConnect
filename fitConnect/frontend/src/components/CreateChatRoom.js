import React, {useState} from 'react';
import axios from 'axios';
import {Button, Modal, Form} from 'react-bootstrap';

const CreateChatRoom = ({eventId}) => {
  const [show, setShow] = useState(false);
  const [title, setTitle] = useState('');
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const [error, setError] = useState('');

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  };

  const createChatRoom = async () => {
    console.log(eventId);
    try {
      await axios.post('/api/chatrooms', {
        title,
        exerciseEventId: eventId,
      });
      setTitle('');
      handleClose();
      alert('채팅방이 성공적으로 생성되었습니다!');
      window.location.href = '/chatRooms'
    } catch (error) {
      const errorMessage = error.response?.data
          || '채팅방 생성에 실패했습니다. 다시 시도해주세요.';
      console.log(errorMessage);
      setError(errorMessage);
    }
  };

  return (
      <>
        <Button variant="primary" onClick={handleShow}>
          채팅방 만들기
        </Button>

        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>채팅방 만들기</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {error && <p className="text-danger">{error}</p>}
            <Form>
              <Form.Group>
                <Form.Label>채팅방 제목</Form.Label>
                <Form.Control
                    type="text"
                    placeholder="채팅방 제목을 입력하세요"
                    value={title}
                    onChange={handleTitleChange}
                />
              </Form.Group>
            </Form>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              닫기
            </Button>
            <Button variant="primary" onClick={createChatRoom}>
              만들기
            </Button>
          </Modal.Footer>
        </Modal>
      </>
  );
};

export default CreateChatRoom;
