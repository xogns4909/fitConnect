import React from 'react';
import axiosInstance from '../global/axiosConfig';
import { Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const RegistrationButton = ({ eventId }) => {
  const navigate = useNavigate();

  const createRegistration = async () => {
    try {
      const response = await axiosInstance.post('/api/registrations/' + eventId, null);
      alert('신청이 완료되었습니다.');
      console.log(response.data);
      navigate('/mypage?tab=registeredEvents');
    } catch (error) {
      const errorMessage = error.response?.data;
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