
# CoMobility ![version Badge](https://img.shields.io/badge/version-0.8%20ver-yellow)
### 2020 창업창의융합동아리 IDE팀 프로젝트
교통약자맞춤 대중교통 경로안내 및 서비스 이용 안드로이드 애플리케이션 **'CoMobility'**

![Android Badge](https://img.shields.io/badge/Android-7.0&nbsp;API&nbsp;Level&nbsp;24-green) ![API Badge](https://img.shields.io/badge/Public&nbsp;OPEN&nbsp;API-blue) ![API Badge](https://img.shields.io/badge/ODsay&nbsp;OPEN&nbsp;API-orange) ![XML](https://img.shields.io/badge/XML-red) ![JSON](https://img.shields.io/badge/JSON-black)

> 교통 약자에게 필요한 교통 편의 요소 또는 보행 장애 요소 정보를 기반으로, 출발지로부터 도착지까지 최적의 경로를 탐색하여 제공하는 방법을 제공하는 교통 약자 맞춤 경로 안내 애플리케이션입니다.
교통약자분들이 더 쉽고 편리하게 접근이 가능하도록 ODsay 대중교통 API와 다양한 서울시 공공데이터 Open API를 활용하여 제작되었습니다.

## 주요 콘텐츠

+ **맞춤 경로 찾기**
    
    앱을 실행하면 사용자가 이용하는 교통약자 편의시설(에스컬레이터,엘리베이터,저상버스,휠체어 캐리어 등)을 선택하여 해당 정보를 바탕으로 편의시설을 사용할 수 있는 최적 경로를 제공합니다.사용자 현위치를 GPS를 통해 불러올 수 있으며 GeoCoding으로 지역명을 검색하여 경로를 찾을 수 있습니다. 경로의 경우 "서울시 버스운행정보 공유서비스"로 저상 버스 정보를 가져오고 편의 시설 리스트를 불러와 Odsay API에 파싱한 정보를 넘겨 사용자에게 경로를 제공합니다.   
    
+ **주변 버스정류장 저상버스 실시간 확인하기**

    구글 맵으로 사용자 현위치 내 500m반경의 정류소를 보여주며 저상버스 현재 위치와 도착 시간을 알립니다. 상단 검색을 통해 원하는 위치의 버스 정류장과 저상 버스 도착 정보를 알 수 있습니다. 저상버스 리스트 옆 호출 버튼을 통해 바로 전화 연결이 가능하도록 하였습니다.

+ **주변 시설 확인하기**

    "서울특별시 장애인 지하철 편의시설 정보" API를 활용하여 역 내부 교통약자 편의 시설의 위치와 해당되는 시설의 이미지 위치를 불러올 수 있도록 하였습니다. 

+ **자주 가는 경로 즐겨찾기**

    경로 검색시 자주 가는 위치를 등록하여 여러번의 검색과정 없이 쉽게 검색이 가능하도록 구현하였습니다. 또한, 자주 가는 경로는 즐겨찾기 내에서 저장할 수 있도록 SQLite를 통해 내부 DB를 구현하였습니다. 자주 가는 경로 리스트를 등록하고 클릭하면 바로 해당되는 경로안내 결과를 볼 수 있습니다.    

## Youtube
[![CoMobility_영상]( https://img.youtube.com/vi/zhNSfLScdwE/0.jpg)](https://youtu.be/zhNSfLScdwE) 

## Team
**신은주** 개발/기획 
  + UI/UX 프론트앤드 개발
  + Google Maps 연결 Android Geo Coding/Reverse Geo Coding을 통한 현위치 변환 리스트 매핑
  + SQLite 즐겨찾기 내부 DB 생성
  
**이정수** 개발/기획
  + 공공 데이터 API parshing
  + ODsey API 공공 데이터 연결 및 길찾기 경로 Data 변환
  
**김가영** 디자인/기획 
  + UI/UX 디자인

## Project URL
https://lib.swu.ac.kr/#/search/detail/4692652?offset=14

## OPEN API URL
+ ODSAY 대중교통 API

    https://lab.odsay.com/guide/guide?platform=android#guideAndroidSDK_2

+ 서울시 대중교통 관련 PUBLIC OPEN API

    http://api.bus.go.kr/contents/sub02/getStaionByRoute.html

    http://115.84.165.39/dataList/OA-15514/A/1/datasetView.do;jsessionid=A5EA1B4F9B1CEA47CEE02247454FA4CA.new_portal-svr-11
