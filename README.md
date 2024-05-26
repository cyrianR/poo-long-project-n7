<a name="readme-top"></a>

[![Java CI with Gradle](https://github.com/cyrianR/poo-long-project-n7/actions/workflows/gradle.yml/badge.svg)](https://github.com/cyrianR/poo-long-project-n7/actions/workflows/gradle.yml)

<!-- PROJECT TITLE -->
<br />
<div align="center">
  <a href="https://github.com/cyrianR/poo-long-project-n7/tree/main">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">N7 Studio</h3>

  <p align="center">
     A student-made digital audio workstation.
    <br />
    <a href=""><strong>Documentation</strong></a>
    <br />
    <br />
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About the project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#authors">Authors</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About the project

This project is a long term project in team of 8 people conducted in the context of the POO courses at ENSEEIHT Engineering School in Toulouse, France. Its goal is to create a Java project with a graphical interface in order to put into practice our learnings.


<!-- BUILT WITH -->
### Built With

Gradle 5.6
Java JDK 11  
Junit 4


<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started


<!-- PREREQUISITES -->
### Prerequisites

Java JDK 11  
Eclipse or VSCode (for gradle tools mostly)

<!-- INSTALLATION -->
### Installation

No need to install gradle on your poersonnal computer. We use gradle wrapper for consistent versioning and ease of use.
In order to use gradle efficiently, follow the steps bellow corresponding to your IDE. 

#### For Eclipse users
```
# Clone the repo
git clone git@github.com:cyrianR/poo-long-project-n7.git  

# Open Eclipse

# Import the repo as a gradle project (specifically a gradle project, not any project import)
- select the File>Import>Gradle>Gradle Project menu entry for this
- click next and add the git repository as the root directory

# Now you can code and build with gradle locally !
```

#### For VSCode users
```
# Download the RedHat extension pack for java

# Download the gradle extension

# Download the Junit tests extension
```
#### For Vim users

```
If you use Vim, you certainly don't need my help.
```


 

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE -->
## Usage

#### Development

For Eclipse and VSCode users, use the integrated gradle tools to build, test and run the project.
Never compile or launch tests yourself without gradle, it's gradle purpose !

You can use gradlew commands directly if you do not want to use Eclipse or VSCode integrated tools :

- build project only : ```./gradlew build```
- build and run project : ```./gradlew run```
- create javadoc : ```./gradlew javadoc```
- help for gradle commands : ```./gradlew tasks```

Gradle computes many files, some of them may be usefull :  

- ```app/build/classes``` : compiled files
- ```app/build/doc``` : javadoc documentation, you can open index.html to consult it
- ```app/build/libs``` : the project computed as a .jar file
- ```app/build/reports``` : tests results


#### Application

User manual will be available at the end of the project. Coming soon...

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- AUTHORS -->
## Authors

Truong Nell  
Mostfa Sami  
Le Nulzec Leandre  
Gauthier Julien  
Cebula Baptiste  
Barilly Victor  
Ragot Cyrian


