# Thread Affinity Wrapper

A wrapper over net.openhft:affinity providing handy classes to create threads pinned to the specified cpus.

## Use

Use the following sample to log a message:

```java
int[] cpus = {0, 1};
AffinityLayout affinity = new FixedAffinityLayout(cpus);

PinnedThreadFactory factory = new PinnedThreadFactory("my-thread", affinity);
Runnable runnable = () -> {
    System.out.println("Hello world!");
    System.out.println("Thread: " + Thread.currentThread());
    System.out.println("Affinity: " + Affinity.getAffinity());
};

Thread thread = factory.newThread(runnable);
thread.start();
```

## Build

Build the project with Gradle and Java 8:
```
./gradlew build
```

## License
 Copyright (C) 2021 EPAM

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

