for f in *.csv ; do
  echo "File -> $f"
  name=$(echo $f | cut -f 1 -d '.')
  mv $f $name
#  sed -i 's/"//g' $f
done

#for i in {1..80}; do 
#for i in 16 73 ; do 
#    echo $i
#    ant run -Dinput=./morpheus/p"$i"_input1.csv -Doutput=./morpheus/p"$i"_output1.csv
#done
