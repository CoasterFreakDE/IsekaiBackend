<div align="center">
<h3 align="center">IsekaiBackend</h3>

  <p align="center">
    A backend for the Isekai Discord Bot and <a href="https://github.com/CoasterFreakDE/AnimeDatabase">AnimeDatabase</a> <a href="https://coasterfreak.de/anime">Website</a>
    <br />
    <br />
  </p>
</div>



<br />

-----
<br />

## 📝 Table of Contents

- [About](#about)
- [Getting Started](#getting_started)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

<br />

## 🧐 About <a name = "about"></a>

This is the backend for the Isekai Discord Bot and Website. It is written in Kotlin and uses the Javalin Framework.

<br />

## 🏁 Getting Started <a name = "getting_started"></a>

### Prerequisites

- [Gradle](https://gradle.org/)
- [Java 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [MongoDB](https://www.mongodb.com/)

### Installing

1. Clone the repository
2. Run `gradle build`
3. Run `gradle shadowJar`
4. Copy the `.env.example` file and rename it to `.env`
5. Fill in the `.env` file
6. Run the jar file
7. Open the website
8. Done!

<br />

## 🎈 Usage <a name="usage"></a>

### Running the jar file

```shell
java -jar IsekaiBackend.jar
```

### Connect via REST
    
    ```shell
    curl -X GET http://localhost:8080/
    ```

<br />

## 🤝 Contributing <a name = "contributing"></a>

Contributions, issues and feature requests are welcome!<br />Feel free to check the issues page.

<br />

## 📝 License <a name = "license"></a>

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

<br />

## 📧 Contact <a name = "contact"></a>

If you have any questions about the project, feel free to contact me via [Discord](https://discord.gg/pixelplayland).
