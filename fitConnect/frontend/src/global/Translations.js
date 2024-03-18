export const translateCity = (cityCode) => {
  const cityTranslations = {
    SEOUL: "서울",
    BUSAN: "부산",
    DAEGU: "대구",
    INCHEON: "인천",
    GWANGJU: "광주",
    DAEJEON: "대전",
    ULSAN: "울산",
    GYEONGGI: "경기도",
    GANGWON: "강원도",
    CHUNGCHEONG: "충청도",
    JEOLLA: "전라도",
    GYEONGSANG: "경상도",
    JEJU: "제주도"
  };
  return cityTranslations[cityCode] || cityCode;
};

export const translateCategory = (categoryCode) => {
  const categoryTranslations = {
    SOCCER: "축구",
    BASKETBALL: "농구",
    BASEBALL: "야구",
    GOLF: "골프",
    FITNESS: "헬스",
    BOWLING: "볼링",
    BILLIARDS: "당구"
  };
  return categoryTranslations[categoryCode] || categoryCode;
};


