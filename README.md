# Cafe-Kiosk-Program-
자바 국비 수업 사이드 프로젝트
프로그램 : 카페 키오스크 프로그램

기능 

1. 카페 메뉴 출력
1.1 커피, 차, 디저트로 카테고리가 3개로 나누어져야 한다.
1.2 카테고리 선택 시 해당 카테고리 상품들이 출력되어야 한다.
1.3 초기 화면에 출력되는 메뉴는 커피이다. 

2. 음료 및 디저트 주문 기능
2.1 주문할 음료와 디저트의 수량을 정할 수 있다.
2.2 음료의  사이즈는 S, M, L 중 선택 할 수 있어야 한다.
2.3 디저트의 사이즈는 기본 사이즈로 한정한다.

3. 장바구니 
3.1 상품 주문 시 장바구니에 상품이 추가되어야 한다.
3.2 장바구니에 존재하는 상품을 추가적으로 담을 경우 수량이 합해져 장바구니에 등록되어야 한다.
3.3 장바구니에 존재하는 상품 수량 또는 사이즈를 변경할 경우 해당 수량, 사이즈로 변경되어야 한다.
3.4 세부 변경, 개별 삭제, 전체 삭제 버튼이 존재해야 한다.
3.5장바구니에서 상품을 선택 후  세부 변경 버튼 클릭 시 해당 상품의 수량, 사이즈를 변경할 수 있어야 한다.
3.6 장바구니에서 상품을 선택 후 개별 삭제 버튼 클릭 시 해당 상품이 장바구니에서 삭제되어야 한다.
3.7 전체 삭제 버튼이 장바구니에 포함된 모든 상품이 삭제되어야 한다.

사용한 DMBS : Oracle
DB 테이블 

테이블 이름 : CAFEMENU (메뉴에 출력할 상품)
1번 컬럼: PRODUCT_NAME(상품명) VARCHAR2(30) PRIMARY KEY
2번 컬럼: CATEGORY_ID(카테고리ID) NUMBER NOT NULL
3번 컬럼: PRODUCT_PRICE(상품 가격) NUMBER NOT NULL
4번 컬럼: PRODUCT_PICTURE(상품사진 파일명) VARCHAR2(30) DEFAULT 'no-image-icon.jpg' 
5번 컬럼: PRODUCT_DESCRIPTION(상품 설명) VARCHAR2(300) DEFAULT '맛있으니 드셔보세요!!'

테이블 이름 : ORDERINFO
1번 컬럼 : ORDER_ID(주문ID) NUMBER NOT NULL
2번 컬럼:  PRODUCT_NAME(상품명) VARCHAR2(30) NOT NULL
3번 컬럼:  PRODUCT_PRICE(상품가격) NUMBER NOT NULL
4번 컬럼 : PRODUCT_QUANTITY (상품 주문 수량) NUMBER NOT NULL
5번 컬럼:  PRODUCT_SIZE (상품 사이즈) VARCHAR2(30) NOT NULL
