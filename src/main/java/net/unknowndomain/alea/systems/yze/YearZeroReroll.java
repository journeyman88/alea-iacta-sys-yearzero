/*
 * Copyright 2020 Marco Bignami.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.unknowndomain.alea.systems.yze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.random.dice.DicePool;
import net.unknowndomain.alea.random.dice.bag.D10;
import net.unknowndomain.alea.roll.GenericResult;
import net.unknowndomain.alea.roll.StatefulRoll;

/**
 *
 * @author journeyman
 */
public class YearZeroReroll extends YearZeroBase implements StatefulRoll
{
    
    private YearZeroResults prev;
    
    public YearZeroReroll(YearZeroModifiers ... mod)
    {
        this(Arrays.asList(mod));
    }
    
    public YearZeroReroll(Collection<YearZeroModifiers> mod)
    {
        super(mod);
    }
    
    @Override
    public GenericResult getResult()
    {
        YearZeroResults results = prev;
        YearZeroResults results2 = null;
        if (mods.contains(YearZeroModifiers.PUSH) && (results.getPrev() == null) )
        {
            List<SingleResult<Integer>> res = new ArrayList<>();
            results2 = results;
            results = buildResults(res, res, res, res);
        }
        results.setPrev(results2);
        results.setVerbose(mods.contains(YearZeroModifiers.VERBOSE));
        return results;
    }
    
    @Override
    public boolean loadState(GenericResult state)
    {
        boolean retVal = false;
        if (state instanceof YearZeroResults)
        {
            prev = (YearZeroResults) state;
            retVal = true;
        }
        return retVal;
    }
    
}
