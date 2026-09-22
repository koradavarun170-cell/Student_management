# Student Management System (Spring Boot + Maven + Jenkins CI/CD)

A simple full-stack Student Management System:
- **Backend:** Spring Boot 3, Java 17
- **DB:** H2 in-memory (no setup needed)
- **Frontend:** Thymeleaf (server-rendered HTML) at `/students`
- **REST API:** `/api/students` (GET, POST, PUT, DELETE) — great for a Postman demo
- **Build tool:** Maven
- **CI/CD:** Jenkins pipeline included (`Jenkinsfile`)

## 1. Run it locally (sanity check before Jenkins)

```bash
mvn clean install
mvn spring-boot:run
```

Then open:
- UI: http://localhost:8080/students
- H2 console: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:studentdb`, user `sa`, no password)
- REST API: http://localhost:8080/api/students

## 2. Push this to GitHub

```bash
cd student-management-system
git init
git add .
git commit -m "Initial commit: Student Management System"
git branch -M main
git remote add origin <YOUR_GITHUB_REPO_URL>
git push -u origin main
```

## 3. Install Jenkins (if not already)

Easiest way — Docker:
```bash
docker run -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts
```
> Note: Jenkins defaults to port 8080, same as this Spring Boot app. Either run Jenkins on a different port
> (`-p 8081:8080`) or change `server.port` in `application.properties`. Recommended: run Jenkins on **8081**.

Open http://localhost:8081, unlock using the password from:
```bash
docker exec <container_id> cat /var/jenkins_home/secrets/initialAdminPassword
```
Install **Suggested Plugins**.

## 4. Configure Jenkins global tools

**Manage Jenkins → Tools:**
- Add a JDK installation named `JDK17`
- Add a Maven installation named `Maven3` (check "install automatically" if unsure of the path)

These names must match what's in the `Jenkinsfile`.

## 5. Create the Pipeline job

1. **New Item → Pipeline** → name it `student-management-cicd`
2. Under **Pipeline**, choose **Pipeline script from SCM**
3. SCM: **Git** → paste your GitHub repo URL
4. Branch: `*/main`
5. Script Path: `Jenkinsfile`
6. Save → **Build Now**

Watch the stages run: Checkout → Build → Test → Package → Deploy.

## 6. (Bonus, for extra CI/CD credit) Auto-trigger on push

- In your GitHub repo: **Settings → Webhooks → Add webhook**
  → Payload URL: `http://<your-jenkins-url>/github-webhook/`
- In Jenkins job config: check **"GitHub hook trigger for GITScm polling"**

Now every `git push` automatically triggers a new build — that's the "CD" part.

## What to show your mam

1. The Jenkins pipeline view with all green stages (Checkout → Build → Test → Package → Deploy)
2. The app running live at `localhost:8080/students` (or whichever port), opened right after the Jenkins build finishes
3. Optional: make a small code change, push to GitHub, and show Jenkins auto-rebuild via the webhook
