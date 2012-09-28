package org.sharkness.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.CalendarType;
import org.hibernate.type.DateType;
import org.hibernate.type.EntityType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;

public class HibernateCriteriaHelper {
	
	private HibernateHelper hibernateHelper;
	
	private Class<?> klassModel;

	public HibernateCriteriaHelper(HibernateHelper hibernateHelper, Class<?> klassModel) {
		this.hibernateHelper = hibernateHelper;
		this.klassModel = klassModel;
	}
	
	public HibernateCriteriaHelper(SessionFactory sessionFactory, Class<?> klassModel) {
		this(new HibernateHelper(sessionFactory), klassModel);
	}
	
	public Criteria setFilters(final Map<String, String> filters, Criteria c, Locale locale) {
		
		if (filters.size() > 0) {
			
			for (Map.Entry<String, String> entry : filters.entrySet()) {
				
				String propertyTypeStr = entry.getKey().split("\\.")[0];
				
				Type propertyType = this.hibernateHelper.getHibernateTypeByNameProperty(klassModel, propertyTypeStr);
				
				if (propertyType instanceof EntityType) c.createAlias(propertyTypeStr, propertyTypeStr);
				
				if (propertyType instanceof IntegerType) {
					
					c.add(Restrictions.like(entry.getKey(), Integer.parseInt(entry.getValue())));
					
				} else if (propertyType instanceof LongType) {
						
					c.add(Restrictions.like(entry.getKey(), Long.parseLong(entry.getValue())));
						
				} else if (propertyType instanceof BigDecimalType) {
					
					c.add(Restrictions.like(entry.getKey(), new BigDecimal(entry.getValue())));
						
				} else if (propertyType instanceof BigIntegerType) {
					
					c.add(Restrictions.like(entry.getKey(), new BigInteger(entry.getValue())));
						
				} else if (propertyType instanceof DateType) {
					
					try {
						
						if (DateTimeHelper.getDateFormatPattern(locale).length() == entry.getValue().length()) {
	
							Date start = DateTimeHelper.getDateFormat(locale).parse(entry.getValue());
							Date end = DateTimeHelper.getDateFormat(locale).parse(entry.getValue());
	
							c.add(Restrictions.between(
								entry.getKey(), 
								DateTimeHelper.lowDate(start),
								DateTimeHelper.highDate(end)
							));
							
						}

					} catch (Exception e) {}
					
				} else if (propertyType instanceof CalendarType) {
					
					try {
						
						if (DateTimeHelper.getDateFormatPattern(locale).length() == entry.getValue().length()) {
	
							Calendar start = DateTimeHelper.parseDateToCalendar(DateTimeHelper.getDateFormat(locale).parse(entry.getValue()));
							Calendar end = DateTimeHelper.parseDateToCalendar(DateTimeHelper.getDateFormat(locale).parse(entry.getValue()));
	
							c.add(Restrictions.between(
								entry.getKey(), 
								DateTimeHelper.lowCalendar(start),
								DateTimeHelper.highCalendar(end)
							));
							
						}

					} catch (Exception e) {}

				} else if (propertyType instanceof TimestampType) {
					
					c.add(Restrictions.eq(entry.getKey(), 
						DateTimeHelper.parseStringToTimestamp(entry.getValue())));

				} else {
					
					c.add(Restrictions.like(entry.getKey(), entry.getValue(), MatchMode.ANYWHERE));
					
				}

			}

		}
		
		return c;
		
	}
	
}