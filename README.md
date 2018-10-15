<!--
Copyright 2016 - 2017 Huawei Technologies Co., Ltd. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

ServiceStage Client (Java)
==========================

The ServiceStage Client provides a simple library to interact with Huawei Cloud's ServiceStage service.

Features
--------

- create application
- update application
- upload binary files to SWR
- check application status
- get servicestage metadata

Installation
------------

Add the client as maven dependency.

Build
-----

`mvn clean package -DskipTests -DfinalName=servicestage-client` to get output jar file with dependencies.


Development
-----------

Import into Eclipse as a Maven Project.

Contributing
------------

Follow the instructions under [Development](development) to get started.

### Formatting

Before submitting changes, make sure to format the code correctly, this will make merging code easier.

Import formatter profile:
- `Window > Preferences > Java > Code Style > Formatter`
- Click Import and Select `JavaFormatter.xml` from the project's root directory.

Next, right-click on the source packages under the project, e.g. `src/main/java` and select `Source > Format`.

License
-------

Apache 2.0. See [LICENSE.txt](LICENSE.txt).

> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>      http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
