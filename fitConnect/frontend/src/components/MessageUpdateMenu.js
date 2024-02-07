import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';

const MessageUpdateMenu = ({ children, onDelete, messageId }) => {
  const [showMenu, setShowMenu] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [menuPosition, setMenuPosition] = useState({ x: 0, y: 0 });
  const [activeMessage, setActiveMessage] = useState(null);

  const handleContextMenu = (event, id) => {
    event.preventDefault();
    setActiveMessage(id);
    setShowMenu(true);
    setMenuPosition({
      x: event.pageX,
      y: event.pageY
    });
  };

  const handleCloseMenu = () => {
    setActiveMessage(null);
    setShowMenu(false);
  };

  const handleShowModal = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleDelete = () => {
    onDelete(messageId);
    handleCloseModal();
  };

  return (
      <div onContextMenu={(e) => handleContextMenu(e, messageId)} onClick={handleCloseMenu} style={{ position: 'relative' }}>
        {showMenu && activeMessage === messageId && (
            <div style={{
            }}>
              <button onClick={handleShowModal}>삭제</button>
            </div>
        )}

        <Modal show={showModal} onHide={handleCloseModal}>
          <Modal.Header closeButton>
            <Modal.Title>삭제 확인</Modal.Title>
          </Modal.Header>
          <Modal.Body>정말로 삭제하시겠습니까?</Modal.Body>
          <Modal.Footer className="d-flex justify-content-center">
            <Button variant="danger" onClick={handleDelete}>
              삭제
            </Button>
          </Modal.Footer>

        </Modal>

        {children}
      </div>
  );
};

export default MessageUpdateMenu;
