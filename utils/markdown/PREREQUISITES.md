
## OSX
### 1 Install graalvm

Using [homebrew](https://github.com/graalvm/homebrew-tap)
```bash
brew cask install graalvm/tap/graalvm-ce-java11
brew cask install graalvm/tap/graalvm-ce-lts-java11
xattr -r -d com.apple.quarantine /Library/Java/JavaVirtualMachines/graalvm-ce-*
```
You can also use sdkman...

### 2 Upgrade xcode dependencies

```bash
xcode-select --install
```


### 3 Install native-image tool

Install the native-image tool using `gu install cmd`
```bash
/Users/guillaumebarthelemy/.sdkman/candidates/java/current/bin/gu install native-image
```
OR
```bash
gu install native-image
```
## Linux

### 1 Install GCC glibc and zlib

```bash
# dnf (rpm-based)
sudo dnf install gcc glibc-devel zlib-devel
# Debian-based distributions:
sudo apt-get install build-essential libz-dev zlib1g-dev
```
You can also use sdkman...

### 2 Install native-image tool
Install the native-image tool using `gu install cmd`
```bash
/Users/guillaumebarthelemy/.sdkman/candidates/java/current/bin/gu install native-image
```
OR
```bash
gu install native-image
```
