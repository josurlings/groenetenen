package be.vdab.restclients;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class Query
{
Results results;
}