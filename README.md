# OpenMPF Java Component SDK

Welcome to the Open Media Processing Framework (OpenMPF) Java Component SDK Project!

## What is the OpenMPF?

The OpenMPF performs content detection and extraction (such as face detection, text extraction, and object classification) on bulk image, video, and audio files, enabling content analysis and search through the extraction of objects, keywords, thumbnails, and other contextual information.

This scalable, web-friendly platform enables users to build configurable multimedia processing pipelines, enabling the rapid development and deployment of analytic algorithms and large-scale media processing applications.

## Overview

This repository contains source code for the Open Media Processing Framework (OpenMPF) Java Component SDK, including the API and associated utilities.

In OpenMPF, a  **component**  is a plugin that receives jobs (containing media), processes that media, and returns results.

Using this API, detection components can be built to provide:

- Detection (Localizing an object)
- Tracking (Localizing an object across multiple frames)
- Classification (Detecting the type of object and optionally localizing that object)
- Transcription (Detecting speech and transcribing it into text)

## Where Am I?

- [Parent OpenMPF Project](https://github.com/openmpf/openmpf-projects)
- [OpenMPF Core](https://github.com/openmpf/openmpf)
- Components
    * [Openmpf Standard Components](https://github.com/openmpf/openmpf-components)
    * [Openmpf Contributed Components](https://github.com/openmpf/openmpf-contrib-components)
- Component APIs:
    * [Openmpf C++ Component sdk](https://github.com/openmpf/openmpf-cpp-component-sdk)
    * [Openmpf Java Component sdk](https://github.com/openmpf/openmpf-java-component-sdk) ( **You are here** )
- [Openmpf Build Tools](https://github.com/openmpf/openmpf-build-tools)
- [OpenMPF Web Site Source](https://github.com/openmpf/openmpf.github.io)

## Getting Started

### Build and Install the Component SDK

- If not already installed, install [Maven](http://maven.apache.org/)
- cd into the `openmpf-java-component-sdk` directory.
- Run: `mvn install`.

### Using the Component SDK

Please read the Java Component API documentation to get started: [https://openmpf.github.io/docs/site/Java-Component-API/](https://openmpf.github.io/docs/site/Java-Component-API/)

## Project Website

For more information about OpenMPF, including documentation, guides, and other material, visit our [website](https://openmpf.github.io/)

## Project Workboard

For a latest snapshot of what tasks are being worked on, what's available to pick up, and where the project stands as a whole, check out our [workboard](https://overv.io/~/openmpf/).

