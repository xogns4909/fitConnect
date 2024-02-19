import React from 'react';
import axios from 'axios';
import { Button } from 'react-bootstrap';

const RegistrationButton = ({ eventId }) => {
  const createRegistration = async () => {
    try {
      const response = await axios.post('/api/registrations/' + eventId, null);
      alert('신청이 완료되었습니다.');
      console.log(response.data);
      window.location.href = '/mypage?tab=registeredEvents'
    } catch (error) {
      const errorMessage = error.response?.data; // 서버로부터 오류 코드 받기
      let message = '신청 중 오류가 발생했습니다.';
      if(errorMessage){
        message = errorMessage;
      }
      console.error(message, error);
      alert(message);
    }
  };

  return <Button variant="primary" onClick={createRegistration}>신청하기</Button>;
};


export default RegistrationButton;