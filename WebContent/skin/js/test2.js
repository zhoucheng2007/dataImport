/** script2 **/
if(window.ns) {
    window.ns.one = 'ONE';
    console.log('window.ns.one:' + window.ns.one);
    window.ns.delay(2);
} else {
    console.log('oops...');
}