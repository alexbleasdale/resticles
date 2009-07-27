CREATE XML SCHEMA COLLECTION dbo.MyNewExampleSchema6
AS 
N'<?xml version="1.0"?>
<xs:schema attributeFormDefault="unqualified" elementFormDefault="qualified" xmlns:xs="http://www.w3.org/2001/XMLSchema">
  <xs:element name="items" type="itemsType"/>
  <xs:complexType name="itemType">
    <xs:sequence>
      <xs:element type="xs:string" name="id"/>
      <xs:element type="xs:string" name="name"/>
      <xs:element type="xs:string" name="number"/>
    </xs:sequence>
  </xs:complexType>
  <xs:complexType name="itemsType">
    <xs:sequence>
      <xs:element type="itemType" name="item" maxOccurs="unbounded" minOccurs="0"/>
    </xs:sequence>
  </xs:complexType>
</xs:schema>';