#for f in ./morpheus/* ; do
#  echo "File -> $f"
#  name=$(echo $f | cut -f 1 -d '.')
#  echo  fuxk $name
#  sed -i 's/"//g' $f
#done

#ulimit -t 300
for i in {3..80}; do 
#for i in 16 73 ; do 
    echo $i
    ant run -Dinput=./morpheus/p"$i"_input1 -Doutput=./morpheus/p"$i"_output1
done
