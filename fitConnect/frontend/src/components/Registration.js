import React from 'react';
import axios from 'axios';
import { Button } from 'react-bootstrap';

const RegistrationButton = ({ eventId }) => {
  const createRegistration = async () => {
    try {
      const response = await axios.post('/api/registrations', null, {
        params: { eventId },
      });
      alert('신청이 완료되었습니다.');
      console.log(response.data);
    } catch (error) {
      console.error('신청 중 오류가 발생했습니다:', error);
      alert('신청 중 오류가 발생했습니다.');
    }
  };

  return (
      <Button variant="primary" onClick={createRegistration}>신청하기</Button>
  );
};

export default RegistrationButton;