function isMouseClickEvent(ev) {
    return ev.type === 'click';
}

function isKeyPressEvent(ev) {
    return ev.type === 'keypress';
}

function isKeyDownEvent(ev) {
    return ev.type === 'keydown';
}

function isKeyUpEvent(ev) {
    return ev.type === 'keyup';
}