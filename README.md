���� - ������ RESTful API ��� ������� �������� ���������.

������ ���������� �� Java Spring Boot 2 � �������������� ���� h2 � �������� ��������� � ������. ������ ������ � �������� ����� ������ ���������� � �������� \src\main\resources � ������ schema.sql � data.sql �����.. ���������� ������ ������� � ��������������������� ����� �������������� � ������� ���������������� ���������� ������� � ������� �������� ������ �� h2. ����� ������ ��������� ������������� �������������� ������, �.�. ��������� ��������� �������� �����������. ����� ������������� ���������� ������ ���������� �� ������ �� (����� AccountBalanceRepositoryTest.java) � ������ ������� � ����� (TransfersControllerTest.java). ������ http - �������� �������� � postman - ��������� \doc\java-rest.postman_collection.json

��� ������ ������� ������������ maven, ��� ���������� ������ � �������� �������� ��������� �������:
  mvn clean package
������ ������� ����� ��������� �������� � �������� ��������:
  mvn spring-boot:run
����� ����������� jar - ���� ����� ��������� ����������� �������, ��������:
  java -jar transfers-0.0.1-SNAPSHOT.jar
�� ��������� ���������� ���������� ���� 8080, ���� ����� ������ ���������� SERVER_PORT