package org.sharkness.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
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
import org.sharkness.logging.support.LoggerFactory;

@SuppressWarnings("serial")
public class HibernateCriteriaHelper implements Serializable {
	
	private HibernateHelper hibernateHelper;
	
	private Class<?> klassModel;

	public Logger getLogger() {
		return LoggerFactory.getLogger();
	}

	public HibernateCriteriaHelper(HibernateHelper hibernateHelper, Class<?> klassModel) {
		this.hibernateHelper = hibernateHelper;
		this.klassModel = klassModel;
	}
	
	public HibernateCriteriaHelper(SessionFactory sessionFactory, Class<?> klassModel) {
		this(new HibernateHelper(sessionFactory), klassModel);
	}
	
	public Criteria setFilters(final Map<String, String> filters, Criteria c, Locale locale) {
	
		getLogger().info(new StringBuilder("HibernateCriteriaHelper.setFilters: filters=")
			.append(filters).append(", criteria=").append(c).append(", locale=").append(locale).toString());
		
		if (filters.size() > 0) {

			getLogger().debug("HibernateCriteriaHelper.setFilters: There are filters to be considered.");
			
			for (Map.Entry<String, String> entry : filters.entrySet()) {
				
				String propertyTypeStr = entry.getKey().split("\\.")[0];
				
				Type propertyType = this.hibernateHelper.getHibernateTypeByNameProperty(klassModel, propertyTypeStr);
				
				if (propertyType instanceof EntityType) c.createAlias(propertyTypeStr, propertyTypeStr);
				
				if (propertyType instanceof IntegerType) {
					
					getLogger().debug("HibernateCriteriaHelper.setFilters: propertyType = IntegerType");
					
					c.add(Restrictions.eq(entry.getKey(), Integer.parseInt(entry.getValue())));
					
				} else if (propertyType instanceof LongType) {
						
					getLogger().debug("HibernateCriteriaHelper.setFilters: propertyType = LongType");

					c.add(Restrictions.eq(entry.getKey(), Long.parseLong(entry.getValue())));
						
				} else if (propertyType instanceof BigDecimalType) {
					
					getLogger().debug("HibernateCriteriaHelper.setFilters: propertyType = BigDecimalType");
					
					c.add(Restrictions.eq(entry.getKey(), new BigDecimal(entry.getValue())));
						
				} else if (propertyType instanceof BigIntegerType) {
					
					getLogger().debug("HibernateCriteriaHelper.setFilters: propertyType = BigIntegerType");

					c.add(Restrictions.eq(entry.getKey(), new BigInteger(entry.getValue())));
						
				} else if (propertyType instanceof DateType) {
					
					getLogger().debug("HibernateCriteriaHelper.setFilters: propertyType = DateType");

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

					} catch (Exception e) {
						
						getLogger().error("HibernateCriteriaHelper.setFilters[DateType]", e);

					}
					
				} else if (propertyType instanceof CalendarType) {
					
					getLogger().debug("HibernateCriteriaHelper.setFilters: propertyType = CalendarType");

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

					} catch (Exception e) {
						
						getLogger().error("HibernateCriteriaHelper.setFilters[CalendarType]", e);

					}

				} else if (propertyType instanceof TimestampType) {
					
					getLogger().debug("HibernateCriteriaHelper.setFilters: propertyType = TimestampType");

					c.add(Restrictions.eq(entry.getKey(), DateTimeHelper.parseStringToTimestamp(entry.getValue())));

				} else {
					
					getLogger().debug("HibernateCriteriaHelper.setFilters: propertyType = StringType");

					c.add(Restrictions.like(entry.getKey(), entry.getValue(), MatchMode.ANYWHERE));
					
				}
				
				getLogger().debug(new StringBuilder("HibernateCriteriaHelper.setFilters: ").append(entry.getKey()).append(" = ").append(entry.getValue()).toString());

			}

		}
		
		return c;
		
	}
	
}