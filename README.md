sharkness-framework
===================

Framework de apoio a criação de aplicações com gerador de código-fonte.

Baseada em spring framework, hibernate, primefaces e maven.

Suporte a CRUD, Autenticação com Spring Security e Spring Remote (HttpInvoker)

- Exemplo do arquivo de configuracao sharkness.cfg.xml completo:

<properties>
	
	<entry key="sharkness.application.name">Test-Artifacts</entry>
	<entry key="sharkness.application.dev.src">src/main/java</entry>
	<entry key="sharkness.application.dev.webapp">src/main/webapp</entry>
	<entry key="sharkness.application.dev.resources">src/main/resources</entry>
	<entry key="sharkness.application.i18n.filename">messages</entry>
	<entry key="sharkness.application.i18n.options">pt_BR,en,fr</entry>
	<entry key="sharkness.application.package">org.contato</entry>
	
	<entry key="sharkness.database.jdbcUrl">jdbc:mysql://localhost/contato</entry>
	<entry key="sharkness.database.driverClass">com.mysql.jdbc.Driver</entry>
	<entry key="sharkness.database.user">root</entry>
	<entry key="sharkness.database.password">root</entry>
	<entry key="sharkness.database.initialPoolSize">5</entry>
	<entry key="sharkness.database.maxPoolSize">10</entry>
	<entry key="sharkness.database.username.hql">from Usuario where username = ?</entry>
	
	<entry key="sharkness.hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</entry>
	<entry key="sharkness.hibernate.ddl">update</entry>
	
	<entry key="sharkness.model.package">entity</entry>
	<entry key="sharkness.dao.package">dao</entry>
	<entry key="sharkness.controller.package">controller</entry>
	<entry key="sharkness.service.package">service</entry>
	<entry key="sharkness.service.impl.package">service.impl</entry>
	<entry key="sharkness.converter.package">converter</entry>
	
	<entry key="sharkness.system.folder">/system</entry>
	<entry key="sharkness.system.manager.folder">/manager</entry>
	
	<entry key="sharkness.page.default">/index.jsf</entry>
	<entry key="sharkness.page.login">/login.jsf</entry>
	<entry key="sharkness.page.access.denied">/login.jsf?denied=true</entry>
	<entry key="sharkness.page.auth.failure">/login.jsf?erro=true</entry>
	<entry key="sharkness.role.admin.value">ROLE_ADMIN</entry>
	<entry key="sharkness.role.remote.value">ROLE_APPLICATION</entry>
	
	<entry key="sharkness.remote.service.folder">/service</entry>
	
	<entry key="sharkness.forceGenerationCode">false</entry>
	<entry key="sharkness.toolbarEnabled">true</entry>
	<entry key="sharkness.webXmlEnabled">true</entry>
	<entry key="sharkness.jsfConfigEnabled">true</entry>
	
	<entry key="sharkness.loggerFileEnabled">true</entry>
	<entry key="sharkness.loggerLevel">ERROR</entry>

</properties>

<properties>

sharkness> help

<properties>
	use esse comando para ajuda
</properties>

</properties>
