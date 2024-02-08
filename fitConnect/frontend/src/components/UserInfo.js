// UserInfo.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Form, FormGroup, Label, Input, Alert } from 'reactstrap';

const UserInfo = () => {
  const [user, setUser] = useState({ nickname: '', email: '' });
  const [editMode, setEditMode] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchUserInfo();
  }, []);

  const fetchUserInfo = async () => {
    try {
      const response = await axios.get('/user');
      console.log(response.data);
      // userBaseInfo 객체에서 nickname과 email을 가져와 상태에 저장합니다.
      setUser({
        nickname: response.data.userBaseInfo.nickname,
        email: response.data.userBaseInfo.email
      });
    } catch (error) {
      console.error('사용자 정보를 불러오는 데 실패했습니다.', error);
      setError('사용자 정보를 불러오는 데 실패했습니다.');
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put('/user', {
        nickname: user.nickname,
      });
      setEditMode(false);
      setError('');
    } catch (error) {
      console.error('사용자 정보를 업데이트하는 데 실패했습니다.', error);
      setError('사용자 정보를 업데이트하는 데 실패했습니다.');
    }
  };

  return (
      <div>
        {error && <Alert color="danger">{error}</Alert>}
        <Form onSubmit={handleSubmit}>
          <FormGroup>
            <Label for="nickname">닉네임</Label>
            <Input type="text" name="nickname" id="nickname" placeholder="닉네임 입력"
                   value={user.nickname} onChange={handleInputChange} readOnly={!editMode} />
          </FormGroup>
          <FormGroup>
            <Label for="email">이메일</Label>
            <Input type="email" name="email" id="email" placeholder="이메일 입력"
                   value={user.email} readOnly />
          </FormGroup>
          {editMode ? (
              <>
                <Button color="primary" type="submit">저장</Button>
                <Button color="secondary" onClick={() => setEditMode(false)}>취소</Button>
              </>
          ) : (
              <Button color="info" onClick={() => setEditMode(true)}>편집</Button>
          )}
        </Form>
      </div>
  );
};

export default UserInfo;
