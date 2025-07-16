function main() {
    data = fazGet("https://localhost:8080/users")
    users = JSON.parse(data)
    console.log(data)
}

// LOGIN
function fazGet(url) {
    let request = new XMLHttpRequest()
    request.open("GET", url, false)
    request.send()
    return request.responseText

}

// CADASTRO
function fazPost(url, body) {
    console.log("Body=", body)
    let request = new XMLHttpRequest()
    request.open("POST", url, true)
    request.setRequestHeader("Content-type", "application/json")
    request.send(JSON.stringify(body))

    request.onload = function() {
        console.log(this.responseText)
    }

    return request.responseText
}

function fazerLogin() {
    event.preventDefault()
    let url = "https://localhost:8080/users"
    let user = document.getElementById("user").value
    let password = document.getElementById("password").value
    console.log(user)
    console.log(password)

    body = {
        "user": user,
        "password": password
    }

    fazPost(url, body)
}
