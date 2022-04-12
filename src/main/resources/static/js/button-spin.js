var elements = document.getElementsByClassName("to-spin");

for (const element of elements) { // You can use `let` instead of `const` if you like
    element.addEventListener("click", (e) => {
        const btn = document.createElement("span");
        btn.setAttribute( 'class', 'spinner-border spinner-border-sm' );
        btn.setAttribute( 'role', 'status' );
        btn.setAttribute( 'aria-hidden', 'true' );
        element.appendChild(btn);
    });
}