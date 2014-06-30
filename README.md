#Jevercookie - evercookies for Java
Jevercookie - java library allowing to use extremely persistent cookies in a browser (evercookies, supercookies) in java projects.
Jevercookie inspired by [original Samy Kamkar's Evercookie project][0] .
##Currently available browser storage mechanisms
- Standard HTTP Cookies
- Browser window.name caching
- Internet Explorer userData storage
- HTML5 Session Storage
- HTML5 Local Storage
- HTML5 Global Storage
- HTML5 Database Storage via SQLite
- ... this list will grow

##Features
- Allowing to use evercookies in java applications
- Tuning of storage mechanism usage
- Hiding evercookies in javascript
- Using as standalone evercookie service for non-java applications

##Examples
You can see examples of jevercookie using in demo directory

###jevercookie/demo/helloworld
Simplest example for using evercookies in java projects

###jevercookie/demo/helloworld_auto
Example of using jevercookies with implicit adding jevercookie javascript link

##Getting started

- 1. Add jevercookie dependency to your project
- 2. Add jevercookie js servlet at your web.xml
```
    <servlet>
        <servlet-name>JecJsServlet</servlet-name>
        <servlet-class>
            com.agenievsky.jevercookie.js.servlet.JecJsServlet
        </servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>JecJsServlet</servlet-name>
        <url-pattern>/jevercookie.js</url-pattern>
    </servlet-mapping>
```
- 3. Use evercookies at your pages
```
 	<script type="text/javascript" src="jevercookie.js"></script>
	<script type="text/javascript">
		window.onload = function() {
			JEC.set("xyz", "0123456789");
			JEC.get("xyz", function f(x) { alert(x); });
		};
	</script>
```

##Usage

##Release notes
[https://github.com/andreygenievsky/jevercookie/wiki/Release-notes](Here)

##License
MIT

##Related projects
- [https://github.com/samyk/evercookie][0] - original Samy Kamkar's Evercookie project
- [https://github.com/gabrielbauman/evercookie-applet][1] - java applet persistence for Evercookie
- [https://github.com/daddyz/evercookie][2] - evercookies for RoR
- [https://github.com/truongsinh/node-evercookie][3] - evercookies for Node.js
- [https://github.com/kevnk/evercookie-mongodb][4] - Evercookie and MongoDB integrations
- [https://github.com/gdmka/django_evercookie][5] - evercookies for Django

HaveFun!

[0]: https://github.com/samyk/evercookie
[1]: https://github.com/gabrielbauman/evercookie-applet
[2]: https://github.com/daddyz/evercookie
[3]: https://github.com/truongsinh/node-evercookie
[4]: https://github.com/kevnk/evercookie-mongodb
[5]: https://github.com/gdmka/django_evercookie
