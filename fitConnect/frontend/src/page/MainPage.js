import React, { useState } from 'react';
import { Modal } from 'react-bootstrap';
import GoogleLoginButton from '../components/GoogleLoginButton';

const MainPage = () => {
  const [showModal, setShowModal] = useState(false);

  const handleShow = () => setShowModal(true);
  const handleClose = () => setShowModal(false);

  return (
      <div className="container">
        <div className="row align-items-center" style={{ height: '80vh' }}>
          <div className="col-md-6 mx-auto text-center">
            <h1 className="mb-4">FitConnect에 오신걸 환영합니다.</h1>
            <p className="lead mb-5">같이 운동할 친구와 선생님을 구해보세요.</p>
            <button className="btn btn-primary btn-lg" onClick={handleShow}>Login</button>
          </div>
        </div>

        <Modal show={showModal} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>Login</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <GoogleLoginButton />
          </Modal.Body>
        </Modal>
      </div>
  );
};

export default MainPage;