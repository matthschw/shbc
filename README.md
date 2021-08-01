# shbc

[![Java CI with Maven](https://github.com/matthschw/shbc/actions/workflows/maven.yml/badge.svg)](https://github.com/matthschw/shbc/actions/workflows/maven.yml)


The SHBC (Simple Hierarchical Binary Container) is a possibility to
store and read

- Numeric Properties
- String Properties
- Boolean Properties
- List of Properties
- Float-Matrices
- Double-Matrices

from and to the hard-disk.
The matrices are stored as binary, the other values are stored in a XML.

# Installation

Clone this repository:

```bash
git clone https://github.com/matthschw/shbc.git
```

`cd shbc` into the directory and install it

```bash
mvn install
```

## Setup
Add the dependency to your project

```xml
<dependency>
  <groupId>edlab.eda</groupId>
  <artifactId>database.shbc</artifactId>
  <version>0.0.1</version>
</dependency>
```

Import the corresponding package to your code
```java
import edlab.eda.database.shbc.*;
```
## API

The [JavaDoc](https://matthschw.github.io/shbc/)
is stored on the Github-Pages (branch *gh-pages*).

## Examples

```java
// Create a new container
Container top = new Container();

// Add a string property
top.addProperty("property1", new StringProperty("value of property 1"));

// Add a numeric property
top.addProperty("property2", new NumericProperty(new BigDecimal(42)));

// Create a list of properties
PropertyList propertyList = new PropertyList();
propertyList.addLast(new StringProperty("value of property 2"));
propertyList.addLast(new StringProperty("value of property 3"));

// Add propertyList to container
top.addProperty("property list", propertyList);

// Create 1-D DoubleMatrixProperty
DoubleMatrixProperty matrix1 = DoubleMatrixProperty
    .create(new double[] { 1.0, 1.6, 1.9 });
top.addProperty("matrix1", matrix1);

// Create 2-D DoubleMatrixProperty
DoubleMatrixProperty matrix2 = DoubleMatrixProperty.create(new double[][] {
    { 4.56, 15455.7, 1119.8 }, { 0.001, 0.5423424, 0.26323 } });
top.addProperty("matrix2", matrix2);

// Create a new container
Container sub = new Container();
top.addProperty("property4", new StringProperty("value of property 4"));

// Add a container to another container
top.addContainer("sub", sub);

File file = new File("./container");

// Save container to hard disk
top.save(file);

// Read container from hard disk
Container c = Container.read(file);
```
# TODO

- [ ] Adding Property Sets
- [ ] Adding Property Maps


## License

Copyright (C) 2021, [Electronics & Drives](https://www.electronics-and-drives.de/)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see 
[https://www.gnu.org/licenses/](https://www.gnu.org/licenses).
