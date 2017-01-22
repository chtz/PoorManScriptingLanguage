function i0() {
var i1=1;
var i2=0;
if (((i1&i2)|(!(i1)&i2))) {
i2=200;
};
if ((i1&(i2|!(i1))&i2)) {
i2=200;
};
if (((i1&i2)|!((i1&i2)))) {
i2=200;
};
return i2;
}