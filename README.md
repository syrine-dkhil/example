# Getting Started

## Pre-requisites

* Git
* JDK 11 or newer
* Maven
* MariaDB
* wget
* unzip

You could use H2 to get started with very little effort, but we want to demonstrate the use of an external database.

## Steps

### 1. Make a Directory for your Project
These instructions will assume that your project and Wildfly instance will be located side-by-side in the same directory.  Once complete, feel free to move the Wildfly installation to another directory.

Open a terminal and create a parent folder (or cd to an existing one):

```sh
mkdir -p <your parent folder>
cd <your parent folder>
```

Example:
```sh
mkdir -p ~/Projects/training
cd ~/Projects/training
```

### 2. Checkout this Source

```sh
git clone git@bitbucket.org:lassitercg/example.git
git checkout security-introduction
```

### 3. Wildfly Installation

```sh
wget https://download.jboss.org/wildfly/19.0.0.Final/wildfly-19.0.0.Final.zip
unzip wildfly-19.0.0.Final.zip
export WILDFLY_HOME=$PWD/wildfly-19.0.0.Final
```

### 4. Configure the MariaDB Wildfly Module

```sh
mkdir -p $WILDFLY_HOME/modules/system/layers/base/com/mariadb/main
wget https://downloads.mariadb.com/Connectors/java/connector-java-2.6.0/mariadb-java-client-2.6.0.jar -o $WILDFLY_HOME/modules/system/layers/base/com/mariadb/main/mariadb-java-client-2.6.0.jar
cp example/src/main/jboss/module.xml $WILDFLY_HOME/modules/system/layers/base/com/mariadb/main/.
```

### 5. Create the Database and Account

Open the mariadb client:
```sh
sudo mariadb -u root
```
**Note:** If you set a password for your root account, include the -p option.

Once at the MariaDB prompt, creae the DB:
```sql
create database example;
grant all privileges on example.* to example@localhost identified by 'localonly';
```

### 6. Start Wildfly

```sh
$WILDFLY_HOME/bin/standalone.sh &
```

### 7. Configure the Database Pool and Security Domain

```sh
$WILDFLY_HOME/bin/jboss-cli.sh -c --file=example/src/main/jboss/setup.cli
```

### 8. Shutdown Wildfly
Assuming no errors, we can now shutdown Wildfly and let our IDE launching it.
To shut down:

```sh
$WILDFLY_HOME/bin/jboss-cli.sh -c command=:shutdown
```
